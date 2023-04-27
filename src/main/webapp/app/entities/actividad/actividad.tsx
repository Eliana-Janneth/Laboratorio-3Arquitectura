import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IActividad } from 'app/shared/model/actividad.model';
import { getEntities } from './actividad.reducer';

export const Actividad = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const actividadList = useAppSelector(state => state.actividad.entities);
  const loading = useAppSelector(state => state.actividad.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="actividad-heading" data-cy="ActividadHeading">
        <Translate contentKey="labArquiJHipsterApp.actividad.home.title">Actividads</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="labArquiJHipsterApp.actividad.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/actividad/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="labArquiJHipsterApp.actividad.home.createLabel">Create new Actividad</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {actividadList && actividadList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="labArquiJHipsterApp.actividad.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="labArquiJHipsterApp.actividad.actividadId">Actividad Id</Translate>
                </th>
                <th>
                  <Translate contentKey="labArquiJHipsterApp.actividad.descripcion">Descripcion</Translate>
                </th>
                <th>
                  <Translate contentKey="labArquiJHipsterApp.actividad.porcentaje">Porcentaje</Translate>
                </th>
                <th>
                  <Translate contentKey="labArquiJHipsterApp.actividad.grupo">Grupo</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {actividadList.map((actividad, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/actividad/${actividad.id}`} color="link" size="sm">
                      {actividad.id}
                    </Button>
                  </td>
                  <td>{actividad.actividadId}</td>
                  <td>{actividad.descripcion}</td>
                  <td>{actividad.porcentaje}</td>
                  <td>{actividad.grupo ? <Link to={`/grupo/${actividad.grupo.id}`}>{actividad.grupo.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/actividad/${actividad.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/actividad/${actividad.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/actividad/${actividad.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="labArquiJHipsterApp.actividad.home.notFound">No Actividads found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Actividad;
