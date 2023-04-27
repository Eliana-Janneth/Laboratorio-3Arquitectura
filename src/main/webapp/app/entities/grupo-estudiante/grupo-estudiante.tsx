import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IGrupoEstudiante } from 'app/shared/model/grupo-estudiante.model';
import { getEntities } from './grupo-estudiante.reducer';

export const GrupoEstudiante = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const grupoEstudianteList = useAppSelector(state => state.grupoEstudiante.entities);
  const loading = useAppSelector(state => state.grupoEstudiante.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="grupo-estudiante-heading" data-cy="GrupoEstudianteHeading">
        <Translate contentKey="labArquiJHipsterApp.grupoEstudiante.home.title">Grupo Estudiantes</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="labArquiJHipsterApp.grupoEstudiante.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/grupo-estudiante/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="labArquiJHipsterApp.grupoEstudiante.home.createLabel">Create new Grupo Estudiante</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {grupoEstudianteList && grupoEstudianteList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="labArquiJHipsterApp.grupoEstudiante.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="labArquiJHipsterApp.grupoEstudiante.grupoEstudianteId">Grupo Estudiante Id</Translate>
                </th>
                <th>
                  <Translate contentKey="labArquiJHipsterApp.grupoEstudiante.grupo">Grupo</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {grupoEstudianteList.map((grupoEstudiante, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/grupo-estudiante/${grupoEstudiante.id}`} color="link" size="sm">
                      {grupoEstudiante.id}
                    </Button>
                  </td>
                  <td>{grupoEstudiante.grupoEstudianteId}</td>
                  <td>{grupoEstudiante.grupo ? <Link to={`/grupo/${grupoEstudiante.grupo.id}`}>{grupoEstudiante.grupo.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/grupo-estudiante/${grupoEstudiante.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/grupo-estudiante/${grupoEstudiante.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/grupo-estudiante/${grupoEstudiante.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="labArquiJHipsterApp.grupoEstudiante.home.notFound">No Grupo Estudiantes found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default GrupoEstudiante;
